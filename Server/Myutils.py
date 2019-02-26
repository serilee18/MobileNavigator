import os, json, csv
import numpy as np
import matplotlib.pyplot as plt
import cv2


def ReadCSV(csvfile):
	filename = csvfile.split('.')[0]
	f1=open(os.path.join('tmp/',csvfile))
	csv_read=csv.reader(f1)
	csv_x=list(csv_read)
	csv_list = [x for x in csv_x if x]
	time_length = len(csv_list)-1
	row = len(csv_list[0])
	#print(csv_length)
	#print(csv_list)

	csv_np = np.asarray(csv_list[1:-1], dtype='float64') # [a[0] for a in csv]
	Start_Time = csv_np[0,0]
	"""
	TYPE_LINEAR_ACCELERATION = A_X, A_Y, A_Z
	TYPE_ORIENTATION = Heading
	TYPE_GYROSCOPE = G_X, G_Y, G_Z
	TYPE_ROTATION_VECTOR = RV_X, RV_Y, RV_Z, RV_W, RV_A

	Time = csv_np[:,0]
	Heading = csv_np[:,1]
	A_X = csv_np[:,2]
	A_Y = csv_np[:,3]
	A_Z = csv_np[:,4]
	G_X = csv_np[:,5]
	G_Y = csv_np[:,6]
	G_Z = csv_np[:,7]
	RV_X = csv_np[:,8]
	RV_Y = csv_np[:,9]
	RV_Z = csv_np[:,10]
	RV_W = csv_np[:,11]
	"""
	for i in range(1,row):
		plt.close('all')
		plt.title(csv_list[0][i])
		plt.plot((csv_np[:,0]-Start_Time)/1000, csv_np[:,i])
		plt.savefig(os.path.join('static/result/',filename,csv_list[0][i]+'.jpg'))

	return csv_list[0]


def GetFrame(VidName,OutPath,NumFrame):
	vid = cv2.VideoCapture(os.path.join('tmp/',VidName))
	success, image = vid.read()
	count = 0
	OutPath = os.path.join('static/images/',OutPath)
	while success and count<NumFrame:
		writepath = os.path.join(OutPath, "frame%d.jpg" % count)
		cv2.imwrite(writepath, image)
		success, image = vid.read()
		count += 1
	print(writepath.split('/')[-1])
	return writepath.split('/')[-1].split('.')[0]
