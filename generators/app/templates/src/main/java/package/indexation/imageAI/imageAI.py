from imageai.Detection import ObjectDetection
from imageai.Prediction import ImagePrediction
import os
import sys
import numpy as np

if len(sys.argv) != 2:
    raise Exception('Invalid number of arguments. 1 argument is required.')

temp_path = str(sys.argv[0]).split("/")
execution_path = '/'.join(temp_path[:-1])
if execution_path:
    execution_path += str('/')

im_name = str(sys.argv[1])
nn_name = 'resnet50_coco_best_v2.0.1.h5'
pre_name = 'resnet50_weights_tf_dim_ordering_tf_kernels.h5'
threshold = 50


detector = ObjectDetection()
detector.setModelTypeAsRetinaNet()
detector.setModelPath(os.path.join(execution_path , nn_name))
detector.loadModel()
detections = detector.detectObjectsFromImage(input_image=im_name, output_image_path=os.path.join(execution_path , "output.jpg") , minimum_percentage_probability=threshold)

predictor = ImagePrediction()
predictor.setModelTypeAsResNet()
predictor.setModelPath(os.path.join(execution_path , pre_name))
predictor.loadModel()
predictions, probabilities = predictor.predictImage(image_input=im_name)


for eachObject in detections:
    #print(eachObject["name"] , " : ", eachObject["percentage_probability"], " : ", eachObject["box_points"] )
    print(eachObject["name"])

for eachPrediction, eachProbability in zip(predictions, probabilities):
    if eachProbability > threshold:
        print(eachPrediction)
