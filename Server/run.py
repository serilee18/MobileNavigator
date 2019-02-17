from flask import Flask, request, jsonify, render_template
from werkzeug import secure_filename
import json
import os

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
        return render_template("index.html")

    #return 'POST_From'

@app.route('/get', methods=['GET'])
def get():
    data = request.args.get('data', default = '*', type = str)
    print(data)
    return 'GOT %s'%data

if __name__ == '__main__':
    app.run(host='163.152.217.166', debug=True,port = 8888)

"""
import csv
 
f = open('1550407407653.csv', 'r', encoding='utf-8')
rdr = csv.reader(f)
lines1=list(rdr)
print(len(lines1))
print(lines1[0])
#for line in rdr:
#    print(line)
f.close()  
"""
