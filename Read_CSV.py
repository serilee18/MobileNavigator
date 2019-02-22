import os, json, csv
import numpy as np
import matplotlib.pyplot as plt


f1=open("1550555990720.csv")
csv=csv.reader(f1)
csv_list=list(csv)
time_length = len(csv_list)-1
row = len(csv_list[0])
#print(csv_length)
#print(csv_list)

csv_np = np.asarray(csv_list[1:], dtype='float64') # [a[0] for a in csv]
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
	plt.savefig(csv_list[0][i]+'.jpg')
