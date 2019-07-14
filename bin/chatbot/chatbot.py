from chatterbot import ChatBot
from chatterbot.trainers import ListTrainer

chatbot = ChatBot(
    'Terminal',
    storage_adapter='chatterbot.storage.SQLStorageAdapter',
    logic_adapters=[
        {
            'import_path': 'chatterbot.logic.BestMatch',
            'default_response': 'Lo siento, pero no entiendo.',
            'maximum_similarity_threshold': 0.70
        }
    ],
    database_uri='sqlite:///database.db'
)

trainer = ListTrainer(chatbot)
trainer.train(
    "./data/"
)


def get_response(text):

    while True:
        if text.strip() != 'Bye':
            result = chatbot.get_response(text)
            reply = str(result)
            print(reply)
            return(reply)
        if text.strip() == 'Bye':
            return('Bye')
            break
