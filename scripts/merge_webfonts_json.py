#!/usr/bin/env python3

import argparse
import json
from pathlib import Path
from typing import Any, Dict, Optional


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description=(
            "Merge static and VF Google Webfonts API responses into one webfonts.json"
        )
    )
    parser.add_argument("--static", dest="static_path", required=True)
    parser.add_argument("--vf", dest="vf_path", required=True)
    parser.add_argument("--out", dest="out_path", required=True)
    return parser.parse_args()


def load_json(path: Path) -> Dict[str, Any]:
    with path.open("r", encoding="utf-8") as fp:
        data = json.load(fp)
    if not isinstance(data, dict):
        raise ValueError(f"Expected JSON object at {path}")
    return data


def merge_font_item(static_item: Dict[str, Any], vf_item: Optional[Dict[str, Any]]) -> Dict[str, Any]:
    merged = dict(static_item)
    if not isinstance(vf_item, dict):
        return merged

    axes = vf_item.get("axes")
    if isinstance(axes, list) and axes:
        merged["axes"] = axes

        vf_files = vf_item.get("files")
        if isinstance(vf_files, dict) and vf_files:
            merged["vfFiles"] = vf_files

        vf_variants = vf_item.get("variants")
        if isinstance(vf_variants, list) and vf_variants:
            merged["vfVariants"] = vf_variants

    return merged


def main() -> None:
    args = parse_args()

    static_root = load_json(Path(args.static_path))
    vf_root = load_json(Path(args.vf_path))

    static_items = static_root.get("items", [])
    vf_items = vf_root.get("items", [])

    if not isinstance(static_items, list):
        raise ValueError("Expected static JSON field 'items' to be a list")
    if not isinstance(vf_items, list):
        raise ValueError("Expected VF JSON field 'items' to be a list")

    vf_by_family = {}
    for item in vf_items:
        if not isinstance(item, dict):
            continue
        family = item.get("family")
        if isinstance(family, str):
            vf_by_family[family] = item

    merged_items = []
    for item in static_items:
        if not isinstance(item, dict):
            continue
        family = item.get("family")
        vf_item = vf_by_family.get(family) if isinstance(family, str) else None
        merged_items.append(merge_font_item(item, vf_item))

    merged_root = dict(static_root)
    merged_root["items"] = merged_items

    out_path = Path(args.out_path)
    with out_path.open("w", encoding="utf-8") as fp:
        json.dump(merged_root, fp, ensure_ascii=False, indent=2)
        fp.write("\n")


if __name__ == "__main__":
    main()
