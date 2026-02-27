#!/usr/bin/env python3
import argparse
import json
import re
from pathlib import Path

def to_camel_case(s):
    """将字符串转换为 PascalCase（用于属性名）"""
    # 移除特殊字符，保留字母、数字和空格
    s = re.sub(r'[^a-zA-Z0-9\s]', '', s)
    # 如果没有空格，直接返回原字符串（保留原有大小写）
    if ' ' not in s:
        return s
    # 如果有空格，分割单词并转换为首字母大写
    words = s.split()
    return ''.join(word.capitalize() for word in words)

def category_to_enum(category_str):
    """将 category 字符串转换为 Category 枚举值"""
    mapping = {
        'display': 'Display',
        'handwriting': 'Handwriting',
        'monospace': 'Monospace',
        'sans-serif': 'SansSerif',
        'serif': 'Serif'
    }
    return mapping.get(category_str, 'SansSerif')

def extract_version(version_str):
    """从版本字符串中提取数字，如 'v23' -> 23"""
    match = re.search(r'v(\d+)', version_str)
    return int(match.group(1)) if match else 1

def format_instant(date_str):
    """将日期字符串转换为 Instant 格式"""
    # 从 "2025-09-08" 转换为 "2025-09-08T00:00:00Z"
    return f"{date_str}T00:00:00Z"

def format_set(items):
    """格式化 Set<String>"""
    if not items:
        return "setOf()"
    quoted = ', '.join(f'"{item}"' for item in sorted(items))
    return f"setOf({quoted})"

def format_map(map_data):
    """格式化 Map<String, String>"""
    if not map_data:
        return "mapOf()"
    entries = ',\n                '.join(
        '"{}" to "{}"'.format(key, value)
        for key, value in sorted(map_data.items())
    )
    return "mapOf(\n                {}\n            )".format(entries)

def format_axes(axes):
    """格式化 axes Set"""
    if not axes:
        return "setOf()"
    
    axis_entries = []
    for axis in axes:
        tag = axis.get('tag', '')
        start = axis.get('start', 0)
        end = axis.get('end', 0)
        axis_entries.append(f'Axis(AxisTag.{tag}, {start}, {end})')
    
    return "setOf(\n                {}\n            )".format(',\n                '.join(axis_entries))

def generate_font_metadata(font_data):
    """为单个字体生成 Kotlin 代码"""
    family = font_data.get('family', '')
    property_name = to_camel_case(family)
    
    version = extract_version(font_data.get('version', 'v1'))
    subsets = font_data.get('subsets', [])
    last_modified = format_instant(font_data.get('lastModified', '2025-01-01'))
    category = category_to_enum(font_data.get('category', 'sans-serif'))
    menu = font_data.get('menu', '')
    
    # Common metadata
    variants = set(font_data.get('variants', []))
    files = font_data.get('files', {})
    
    # Variable font metadata
    vf_files = font_data.get('vfFiles', {})
    vf_variants = set(font_data.get('vfVariants', []))
    axes = font_data.get('axes', [])
    
    has_variable_font = bool(vf_files or vf_variants or axes)
    
    code = f'''val GoogleFontFamilyMetadata.Companion.{property_name}
    get() = GoogleFontFamilyMetadata(
        name = "{family}",
        version = {version},
        subsets = {format_set(subsets)},
        lastModified = Instant.parse("{last_modified}"),
        category = Category.{category},
        menu = "{menu}",
        commonMetadata = GoogleFontFamilyMetadata.CommonMetadata(
            variants = {format_set(variants)},
            files = {format_map(files)}
        ),
        variableFontMetadata = {format_variable_font_metadata(vf_variants, vf_files, axes) if has_variable_font else 'null'}
    )
'''
    return code

def format_variable_font_metadata(vf_variants, vf_files, axes):
    """格式化 VariableFontMetadata"""
    return f'''GoogleFontFamilyMetadata.VariableFontMetadata(
            variants = {format_set(vf_variants)},
            files = {format_map(vf_files)},
            axes = {format_axes(axes)}
        )'''


def get_default_paths(base_dir):
    input_path = base_dir / 'webfonts.json'
    output_path = base_dir / 'google-fonts/src/commonMain/kotlin/com/adamglin/async/font/google/fonts/GoogleFonts.kt'
    return input_path, output_path

def parse_args():
    parser = argparse.ArgumentParser(description='生成 GoogleFonts.kt 字体元数据')
    parser.add_argument('--input', type=Path, help='webfonts.json 文件路径')
    parser.add_argument('--output', type=Path, help='GoogleFonts.kt 输出路径')
    return parser.parse_args()


def main():
    args = parse_args()
    script_dir = Path(__file__).resolve().parent
    default_input, default_output = get_default_paths(script_dir)
    input_path = args.input or default_input
    output_path = args.output or default_output

    # 读取 webfonts.json
    with open(input_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
    
    # 生成 Kotlin 代码
    output = []
    output.append("package com.adamglin.async.font.google.fonts")
    output.append("")
    output.append("import kotlin.time.Instant")
    output.append("")
    
    fonts = data.get('items', [])
    for i, font in enumerate(fonts):
        if i > 0:
            output.append("")
        output.append(generate_font_metadata(font))
    
    # 写入文件
    output_path.parent.mkdir(parents=True, exist_ok=True)
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write('\n'.join(output))
    
    print(f"已生成 {len(fonts)} 个字体的元数据")

if __name__ == '__main__':
    main()
