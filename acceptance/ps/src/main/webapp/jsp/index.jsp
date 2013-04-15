<%@ taglib uri="http://togglz.org/spring/taglib" prefix="togglz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Hello World</title>
</head>
<body>
    <h:outputStylesheet name="style.css" library="css" />
    <p>This is an example of using feature toggles via WS.</p>
    <p>If you can't see the button 'Say hello', go to the WS Admin
        Console and enable the feature 'ID_2'.</p>
    <div id="container">
        <h1>Hello World</h1>
        <togglz:activeFeature name="ID_2">
            <input type="button" value="Say hello" onclick="doIt()"> 
        </togglz:activeFeature>
    </div>
    <div>
         <a href="http://localhost:8080/ws/togglz/">WS Admin Console</a>
    </div>
</body>
</html>

