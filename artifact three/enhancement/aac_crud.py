from pymongo import MongoClient
from bson.objectid import ObjectId


class AnimalShelter(object):
    def __init__(self,username,password):
        self.USER = username
        self.PASS = password
        self.DB = 'AAC'
        self.COL = 'animals'

        try:
            self.client = MongoClient(f"mongodb://{username}:{password}@localhost:27017")
            self.database = self.client[self.DB]
            self.collection = self.database[self.COL]
            #print("Connected to the database successfully.")
        except Exception as e:
            print(f"An error occurred: {e}")

    def create(self, data):
        if data is not None:  # Check if data is not empty
            insert_result = self.database.animals.insert_one(data)  # Insert data into the database
            if insert_result.inserted_id:  # Check if data was inserted
                return True
            else:
                return False
        else:  # If data is empty
            raise Exception("Nothing to save, because data parameter is empty")

    def read(self, data):
        if data is not None:  # Check if data is not empty
            result = self.database.animals.find(data)  # Find data in the database
            return list(result)  # Return the result as a list, will return an empty list if no data is found
        else:
            raise Exception("Nothing to search, because data parameter is empty")  # If data is empty

    def update(self, data, new_data, multi=False):
        if data is not None and new_data is not None:  # Check if data and new_data are not empty
            if not multi:  # If multi is False
                update_result = self.database.animals.update_one(data, {'$set': new_data})
            else:  # if true
                update_result = self.database.animals.update_many(data, {'$set': new_data})
            return update_result.modified_count  # Return number of modified documents
        else:  # If data or new_data is empty
            raise Exception("Nothing to update, because data parameter is empty")

    def delete(self, data, multi=False):  # Delete data from the database
        if data is not None:  # Check if data is not empty
            if multi == False:  # Check if multi is False
                delete_result = self.database.animals.delete_one(data)
            else:
                delete_result = self.database.animals.delete_many(data)
            return delete_result.deleted_count
        else:
            raise Exception("Nothing to delete, because data parameter is empty")