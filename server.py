
from flask import Flask, request, jsonify
import json

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Korea_Univ_By_Ai.Math.Lab'

@app.route('/post', methods=['POST'])
def post():
    return 'POST_From'

@app.route('/get', methods=['GET'])
def get():
    data = request.args.get('data', default = '*', type = str)
    print(data)
    return 'GOT %s'%data

if __name__ == '__main__':
    app.run(host='163.152.217.166', debug=True,port = 8888)

