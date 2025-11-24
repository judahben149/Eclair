# TensorFlow Lite Model Assets

## Current Setup (Testing with Pre-trained MobileNet)

Currently using:
- **Model**: `equipment_classifier.tflite` (pre-trained MobileNet v1 quantized)
- **Labels**: `imagenet_labels.txt` (1001 ImageNet classes)
- **Backups**:
  - `equipment_labels.txt.backup` (your 12 custom equipment labels)
  - `equipment_classifier_untrained.tflite.backup` (untrained model)

## How Label Loading Works

The classifier automatically tries to load labels in this priority order:

1. **equipment_labels.txt** → For your custom lighting equipment model ⭐
2. **imagenet_labels.txt** → For testing with standard MobileNet (current)
3. **Hardcoded labels** → Fallback if no files found

Check logcat with filter "Classifier" to see which labels file is being loaded.

## Required Files

### equipment_classifier.tflite
Your trained TensorFlow Lite model file for equipment classification.

**Model Specifications:**
- Input: 224x224 RGB image (float32)
- Output: Probability array for each equipment category
- Categories: See `equipment_labels.txt`

### equipment_labels.txt
List of equipment categories (one per line).
Currently backed up as `equipment_labels.txt.backup`

### imagenet_labels.txt
Standard ImageNet 1001 class labels for testing with MobileNet.

## Switching to Your Custom Equipment Model

When you're ready to use your trained equipment model:

```bash
# Restore equipment labels
mv equipment_labels.txt.backup equipment_labels.txt

# Replace with your trained model
# Just overwrite equipment_classifier.tflite with your trained model
```

The code will automatically detect and use `equipment_labels.txt` when it's present!

## Training Your Model

Follow the instructions in `EQUIPMENT_IDENTIFICATION_SETUP.md` in the project root to train your own model.
