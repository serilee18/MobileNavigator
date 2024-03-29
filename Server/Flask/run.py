from flask import Flask, request, jsonify, render_template
from werkzeug import secure_filename
import os, json, csv
import numpy as np
import matplotlib.pyplot as plt
import Myutils

app = Flask(__name__)

app.config['UPLOAD_FOLDER'] = 'tmp' 

@app.route('/')
def hello_world():

    return render_template("index.html")
    #return 'Korea_Univ_By_Ai.Math.Lab'

@app.route('/upload')
def upload_file():
   return render_template('upload.html')


@app.route('/post', methods=['POST'])
def post():
    value = request.form['aimathlab']
    if value!= "KoreaUniv":
        return "[ERROR]_WRONG_FILE_[ERROR]"
    else:
        f_csv = request.files['csv']
        f_vid = request.files['vid']
        f_csv.save(os.path.join(app.config['UPLOAD_FOLDER'], secure_filename(f_csv.filename)))
        f_vid.save(os.path.join(app.config['UPLOAD_FOLDER'], secure_filename(f_vid.filename)))
        #filename = (f_csv.filename).split('.')[0]
        #print(filename)
        #os.mkdir(os.path.join('static/result',filename))
        VideoNames = Myutils.GetFrame(f_vid.filename,'',2)
        ImageNames =  Myutils.ReadCSV(f_csv.filename)
        #ResultImages = os.listdir(os.path.join('static/result/',filename))
        ResultImages = os.listdir('static/result/')
        print(ImageNames)
        return ','.join(ImageNames)+','+VideoNames
        #return "Server OK"
        #return render_template("result.html", ResultImages=ResultImages)

    #return 'POST_From'

@app.route('/get', methods=['GET'])
def get():
    data = request.args.get('data', default = '*', type = str)
    print(data)
    return 'GOT %s'%data

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True,port = 8888)

