#!/bin/bash

#SBATCH -J MobileNavi   # job name
#SBATCH -o Processing.log   # standard output and error log
#SBATCH --time=90:00:00   # Time limit hh:mm:ss
#SBATCH -p gpu
#SBATCH --gres=gpu:1
source /home/rbfpark/venv/MN3/bin/activate
python main.py
