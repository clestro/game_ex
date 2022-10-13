# GAME EXAMPLE

## Database Configs
>**Creating Database Postgres**
* Create db with db_name - game_ex on localhost:5432
* user=postgres
* password=root

>**To create tables run RunToCreateTables.sql in resources**

## Created data

>* Tasks list : 
>* Task{id=1, task=ARRANGE_A_FAIR, gold=10}
>* Task{id=2, task=CAPTURE_THE_VILLAGE, gold=15}
>* Task{id=3, task=ROB_MERCHANTS, gold=35}

>* Users list :
>* User{id=1, username='player1'}
>* User{id=2, username='player2'}
>* User{id=3, username='player3'}

## EndPoints
>To add clan post localhost:8000/api/clan/add with requestBody {"name":"clanName"}

>To get clan by id get localhost:8000/api/clan/get?id=9

>To add gold by task post localhost:8000/api/task/complete with requestBody 
{
"clanId":"value",
"taskId":"value"
}

>To add gold by user post localhost:8000/api/user/add-gold with requestBody
{
"userId": "value",
"clanId": "value",
"gold": "value"
}

>To get gold history by clan post localhost:8000/api/history/by-clan
{
"clanId":"value"
}