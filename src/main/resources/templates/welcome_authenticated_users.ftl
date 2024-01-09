<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to MarketHub</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Ubuntu:wght@400;700&display=swap');

        body {
            margin: 0;
            padding: 0;
        }

        .email-container {
            text-align: center;
            background-color: #24243E;
            width: 612px;
            margin: 0 auto;
            padding: 20px;
        }

        .header {
            margin-top: 20px;
            text-align: center;
            color: #A7B0FF;
            font-size: 12px;
        }

        .welcome {
            font-size: 24px;
        }

        .slogan {
            font-size: 12px;
        }

        .bold-markethub {
            font-weight: bold;
        }

        .line {
            width: 390px;
            height: 1px;
            background-color: #ffffff;
            margin: 20px auto;
        }

        .message {
            width: 390px;
            font-size: 12px ;
            color: white;
            margin: 20px auto;
        }

        .hello-user {
            text-align: left;
            font-size: 16px;
        }

        .text {
            text-align: left;
        }

        .button {
            width: 155px;
            text-align: center;
            background-color: #7F33CB;
            color: #ffffff;
            font-size: 16px;
            padding: 10px 20px;
            border: none;
            border-radius: 1px;
            cursor: pointer;
        }

        .support {
            text-align: left;
            width: 355px;
            font-size: 12px;
            color: white;
            background-color: #592ED5;
            padding: 10px 20px;
            border-radius: 5px;
            margin: 20px auto;
        }

        .support-title {
            font-size: 16px;
        }

        .support-text {
            margin: 5px 0;
        }

        .footer {
            margin-top: 50px;
            text-align: center;
            font-size: 10px;
            color: white;
        }

    </style>
</head>
<body>
<div class="email-container">
    <div class="header">
        <p class="welcome">Welcome to <span class="bold-markethub">MarketHub</span><br>
            <span class="slogan">«The Best Electronic Marketplace»</span>
        </p>
    </div>
    <div class="line"></div>
    <div class="message">
        <p class="hello-user">Hi, ${firstname} ${lastname}!</p>
        <p class="text">Your account has been successfully created. Click the button below to go to your profile.</p>
        <button class="button">Profile</button>
    </div>
    <div class="line"></div>
    <div class="support">
        <p class="support-title">Help & support</p>
        <p class="support-text">If you have any questions, feel free to contact our <a href="http://localhost:8080" style="color: #00FFFF;">support team</a>.</p>
    </div>
    <div class="line"></div>
    <div class="footer">
        <p>Copyright © 2023 MarketHub </p>
    </div>
</div>
</body>
</html>



