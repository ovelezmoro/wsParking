from chatterbot import ChatBot
#from chatterbot.trainers import ListTrainer
#import os

chatbot = ChatBot(
    'Terminal',
    storage_adapter='chatterbot.storage.SQLStorageAdapter',
    logic_adapters=[
        {
            'import_path': 'chatterbot.logic.BestMatch',
            'default_response': 'Lo siento, pero no entiendo la pregunta realizada.',
            'maximum_similarity_threshold': 0.70
        }
    ],
    input_adapter="chatterbot.input.TerminalAdapter",
    output_adapter="chatterbot.output.TerminalAdapter",
    database_uri=None,
    read_only=True
)

def get_response(text):

    while True:
        if text.strip() != 'Hasta luego':
            result = chatbot.get_response(text)
            reply = str(result)
            print(reply)
            return(reply)
        if text.strip() == 'Hasta luego':
            return('Muchas gracias por usar nuestro servicio')
        break
