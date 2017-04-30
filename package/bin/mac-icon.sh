#!/bin/bash

set -e

SOURCE_FILE=$1
DESTINATION_DIR=$2
NAME=$3

SCRATCH="$DESTINATION_DIR/$NAME".iconset

mkdir -p "$SCRATCH"
convert "$SOURCE_FILE" -resize 16x16! "$SCRATCH"/icon_16x16.png
convert "$SOURCE_FILE" -resize 32x32! "$SCRATCH"/icon_32x32.png
convert "$SOURCE_FILE" -resize 128x128! "$SCRATCH"/icon_128x128.png
convert "$SOURCE_FILE" -resize 256x256! "$SCRATCH"/icon_256x256.png
convert "$SOURCE_FILE" -resize 512x512! "$SCRATCH"/icon_512x512.png
png2icns "$DESTINATION_DIR/$NAME.icns" "$SCRATCH"/icon_*.png
rm -R "$SCRATCH"
