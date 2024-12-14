from jupyter_dash import JupyterDash
from dotenv import load_dotenv
import dash_leaflet as dl
from dash import dcc
from dash import html
import plotly.express as px
from dash import dash_table
from dash.dependencies import Input, Output, State
import base64
import os


import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

# change animal_shelter and AnimalShelter to match your CRUD Python module file name and class name
from aac_crud import AnimalShelter

###########################
# Data Manipulation / Model
###########################
# FIX ME update with your username and password and CRUD Python module name


# Connect to database via CRUD Module
load_dotenv()
username = os.getenv("MONGO_USER")
password = os.getenv("MONGO_PASS")
db = AnimalShelter(username, password)

# class read method must support return of list object and accept projection json input
# sending the read method an empty document requests all documents be returned
df = pd.DataFrame.from_records(db.read({}))

# MongoDB v5+ is going to return the '_id' column and that is going to have an
# invlaid object type of 'ObjectID' - which will cause the data_table to crash - so we remove
# it in the dataframe here. The df.drop command allows us to drop the column. If we do not set
# inplace=True - it will reeturn a new dataframe that does not contain the dropped column(s)
df.drop(columns=['_id'], inplace=True)

## Debug
# print(len(df.to_dict(orient='records')))
# print(df.columns)


#########################
# Dashboard Layout / View
#########################
app = JupyterDash(__name__)

# FIX ME Add in Grazioso Salvare’s logo
image_filename = 'logo.png'  # replace with your own image
encoded_image = base64.b64encode(open(image_filename, 'rb').read())
app.layout = html.Div([
    html.Div(id='hidden-div', style={'display': 'none'}),
    html.A(
        href='https://www.snhu.edu',
        target='_blank',
        children=html.Img(
            src='data:image/png;base64,{}'.format(encoded_image.decode()),
            style={
                'display': 'block',
                'margin-left': 'auto',
                'margin-right': 'auto',
                'width': '200px'
            }
        )
    ),
    html.Center(html.B(html.H1('Vy Huynh Dashboard'))),
    html.Hr(),
    html.Div(


        dcc.RadioItems( # button to filter by type
            id='filter-type',
            options=[
                {'label': 'Water Rescue', 'value': 'Water Rescue'}, # options water rescue
                {'label': 'Mountain Rescue', 'value': 'Mountain Rescue'}, # options mountain rescue
                {'label': 'Disaster Rescue', 'value': 'Disaster Rescue'}, # options disaster rescue
                {'label': 'Reset', 'value': 'Reset'} # reset button
            ],
            value='Reset',
            labelStyle={'display': 'inline-block'}
        )
    ),
    html.Hr(),
    html.H5("Animal Type", style={'margin-bottom': '5px'}), dcc.Checklist( # checklist for animal type
        id='animal-type',
        options=[
            {'label': 'Dog', 'value': 'Dog'}, # options dog
            {'label': 'Cat', 'value': 'Cat'}, # options cat
            {'label': 'Other', 'value': 'Other'}, # options other
        ],
        labelStyle={'display': 'inline-block', 'margin-right': '5px'}
    ),
    html.Hr(),

    dash_table.DataTable(
        id='datatable-id',
        columns=[
            {"name": i, "id": i, "deletable": False, "selectable": True} for i in df.columns

        ],
        data=df.to_dict('records'),
        selected_rows=[0],  # the row selected by default
        row_selectable="single",  # single row
        page_size=10,  # number of rows per page
        sort_action="native",  # enable data to be sorted
        filter_action="native",  # enable data to be filtered
    ),

    html.Br(),
    html.Hr(),
    # This sets up the dashboard so that your chart and your geolocation chart are side-by-side
    html.Div(className='row',
             style={'display': 'flex'},
             children=[
                 html.Div(
                     id='graph-id',
                     className='col s12 m6',

                 ),
                 html.Div(
                     id='map-id',
                     className='col s12 m6',
                 )
             ])
])


#############################################
# Interaction Between Components / Controller
#############################################


@app.callback(
    [Output('datatable-id', 'data'),
     Output('datatable-id', 'columns')],
    [Input('filter-type', 'value'),
     Input('animal-type', 'value')])

def update_dashboard(filter_type,animal_type):
    df = pd.DataFrame()

    # Retrieve data based on filter_type
    if filter_type == 'Reset' and (not animal_type or animal_type == ['']): # if filter_type is 'Reset' and animal_type is empty
        df = pd.DataFrame.from_records(db.read({}))
    elif filter_type == 'Water Rescue': # if filter_type is 'Water Rescue'
        query = {
            "$or": [
                {"breed": {"$regex": "Labrador Retriever Mix", "$options": "i"}},
                {"breed": {"$regex": "Chesapeake Bay Retriever", "$options": "i"}},
                {"breed": {"$regex": "Newfoundland", "$options": "i"}}
            ],
            "sex_upon_outcome": "Intact Female",
            "age_upon_outcome_in_weeks": {"$gte": 26, "$lte": 156}
        }
        df = pd.DataFrame.from_records(db.read(query))
    elif filter_type == 'Mountain Rescue': # if filter_type is 'Mountain Rescue'
        query = {
            "$or": [
                {"breed": {"$regex": "German Shepherd", "$options": "i"}},
                {"breed": {"$regex": "Alaskan Malamute", "$options": "i"}},
                {"breed": {"$regex": "Old English Sheepdog", "$options": "i"}},
                {"breed": {"$regex": "Siberian Husky", "$options": "i"}},
                {"breed": {"$regex": "Rottweiler", "$options": "i"}}
            ],
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte": 26, "$lte": 156}
        }
        df = pd.DataFrame.from_records(db.read(query))

    elif filter_type == 'Disaster Rescue': # if filter_type is 'Disaster Rescue'
        query = {
            "$or": [
                {"breed": {"$regex": "Doberman Pinscher", "$options": "i"}},
                {"breed": {"$regex": "German Shepherd", "$options": "i"}},
                {"breed": {"$regex": "Golden Retriever", "$options": "i"}},
                {"breed": {"$regex": "Bloodhound", "$options": "i"}},
                {"breed": {"$regex": "Rottweiler", "$options": "i"}}
            ],
            "sex_upon_outcome": "Intact Male",
            "age_upon_outcome_in_weeks": {"$gte": 26, "$lte": 156}
        }
        df = pd.DataFrame.from_records(db.read(query))

    if animal_type is not None: # if animal_type is not empty
        query = {
            "animal_type": {"$in": animal_type} # filter by animal_type
        }
        df = pd.DataFrame.from_records(db.read(query))

    if df.empty:
        return [], []

    hide_columns = ['location_lat', 'location_long', 'age_upon_outcome_in_weeks',''] # columns to hide

    if '_id' in df.columns:
        df = df.drop(columns=['_id'])

    column_mapping = {
        "age_upon_outcome": "Age Upon Outcome",
        "animal_id": "Animal ID",
        "animal_type": "Animal Type",
        "breed": "Breed",
        "color": "Color",
        "date_of_birth": "Date of Birth",
        "datetime": "Datetime",
        "monthyear": "Month/Year",
        "name": "Name",
        "outcome_subtype": "Outcome Subtype",
        "outcome_type": "Outcome Type",
        "sex_upon_outcome": "Sex Upon Outcome",
    } # rename columns
    df.rename(columns=column_mapping, inplace=True) # rename columns

    columns = []

    for i in df.columns: # loop through columns
        if i not in hide_columns: # if i is not in hide_columns
            columns.append({"name": column_mapping.get(i, i), "id": i, "deletable": False, "selectable": True})
    data = df.to_dict('records')
    return data, columns


@app.callback(
    Output('graph-id', "children"),
    [Input('datatable-id', "derived_virtual_data")])
def update_graphs(viewData):
    # Convert the input data into a DataFrame
    df = pd.DataFrame.from_dict(viewData)
    df.columns = df.columns.astype(str).str.strip()
    df.columns = [col.lower() for col in df.columns]

    # Check if the DataFrame is not empty to prevent errors
    if df.empty:
        return []

    fig = px.pie(df, names='breed')

    return [
        dcc.Graph(
            figure=fig
        )
    ]


# This callback will highlight a cell on the data table when the user selects it
@app.callback(
    Output('datatable-id', 'style_data_conditional'),
    [Input('datatable-id', 'selected_columns')]
)
def update_styles(selected_columns):
    if selected_columns is None: # if selected_columns is empty
        return []

    return [{
        'if': {'column_id': i},
        'background_color': '#D2F3FF'
    } for i in selected_columns]



@app.callback(
    Output('map-id', "children"),
    [Input('datatable-id', "derived_virtual_data"),
     Input('datatable-id', "derived_virtual_selected_rows")])
def update_map(viewData, index):
    dff = pd.DataFrame.from_dict(viewData)

    if index is None:
        row = 0
    else:
        row = index[0]

    if len(dff) == 0:
        print("No data available.")
        return []

    if 'location_lat' in dff.columns and 'location_long' in dff.columns: # Check if the columns exist
        lat = dff.iloc[row, dff.columns.get_loc('location_lat')] # Get the latitude
        long = dff.iloc[row, dff.columns.get_loc('location_long')] # Get the longitude
    else:
        return [] # return empty

    return [
        dl.Map(style={'width': '1000px', 'height': '500px'},
               center=[30.75, -97.48], zoom=10, children=[
                dl.TileLayer(id="base-layer-id"),
                dl.Marker(position=[lat, long],
                          children=[
                              dl.Tooltip(dff.iloc[row, 4]),
                              dl.Popup([
                                  html.H1(dff.iloc[row, 9]),
                                  html.P(dff.iloc[row, 4])
                              ])
                          ])
            ])
    ]


app.run_server(debug=True)
