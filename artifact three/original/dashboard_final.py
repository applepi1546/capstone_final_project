# Setup the Jupyter version of Dash
from jupyter_dash import JupyterDash

# Configure the necessary Python module imports for dashboard components
import dash_leaflet as dl
from dash import dcc
from dash import html
import plotly.express as px
from dash import dash_table
from dash.dependencies import Input, Output, State
import base64

# Configure OS routines
import os

# Configure the plotting routines
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

#### FIX ME #####
# change animal_shelter and AnimalShelter to match your CRUD Python module file name and class name
from aac_crud import AnimalShelter

###########################
# Data Manipulation / Model
###########################
# FIX ME update with your username and password and CRUD Python module name

username = "aacuser"
password = "SNHU1234"

# Connect to database via CRUD Module
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


        dcc.RadioItems(
            id='filter-type',
            options=[
                {'label': 'Water Rescue', 'value': 'Water Rescue'},
                {'label': 'Mountain Rescue', 'value': 'Mountain Rescue'},
                {'label': 'Disaster Rescue', 'value': 'Disaster Rescue'},
                {'label': 'Reset', 'value': 'Reset'}
            ],
            value='Reset',
            labelStyle={'display': 'inline-block'}
        )
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
    [Input('filter-type', 'value')]
)
def update_dashboard(filter_type):
    df = pd.DataFrame()

    # Retrieve data based on filter_type
    if filter_type == 'Reset':
        df = pd.DataFrame.from_records(db.read({}))
    elif filter_type == 'Water Rescue':
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

    elif filter_type == 'Mountain Rescue':
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

    elif filter_type == 'Disaster Rescue':
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


    if df.empty:
        return [], []

    if '_id' in df.columns:
        df = df.drop(columns=['_id'])

    columns = [{"name": i, "id": i, "deletable": False, "selectable": True} for i in df.columns]
    data = df.to_dict('records')

    return data, columns


@app.callback(
    Output('graph-id', "children"),
    [Input('datatable-id', "derived_virtual_data")])
def update_graphs(viewData):
    # Convert the input data into a DataFrame
    df = pd.DataFrame.from_dict(viewData)

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

    return [
        dl.Map(style={'width': '1000px', 'height': '500px'},
               center=[30.75, -97.48], zoom=10, children=[
                dl.TileLayer(id="base-layer-id"),
                dl.Marker(position=[dff.iloc[row, 13], dff.iloc[row, 14]],
                          children=[
                              dl.Tooltip(dff.iloc[row, 4]),
                              dl.Popup([
                                  html.H1(dff.iloc[row, 9]),
                                  html.P(dff.iloc[row, 4])
                              ])
                          ])
            ])
    ]


app.run_server(debug=False)