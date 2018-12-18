from flask import Flask
from flask import request
import webbrowser

app = Flask(__name__)


def shutdown_server():
    func = request.environ.get('werkzeug.server.shutdown')
    if func is None:
        raise RuntimeError('Not running with the Werkzeug Server')
    func()


@app.route('/shutdown', methods=['POST'])
def shutdown():
    shutdown_server()
    return 'Server shutting down Pyesser...'


@app.route('/<param>', methods=['POST'])
def simple_request(param):
    return param + ' pythoned'


@app.route('/', methods=['POST'])
def complex_request():
    json = request.get_json()
    text_file = open("Output.txt", "a")
    text_file.write("Request: %s \n" % json)
    text_file.close()

    webbrowser.open("Output.txt")
    return "Processed to file"


if __name__ == '__main__':
    app.run()
