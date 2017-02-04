<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.css" rel="stylesheet">

</head>
<body>

<div class="testbox">
    <h1>${message}</h1>

    <c:url var="homeURL" value="home.jsp"/>
    <form action="${homeURL}">
        <hr>
        <div class="accounttype">
            <input type="radio" value="None" id="radioOne" name="account" checked/>
            <label for="radioOne" class="radio">Personal</label>
            <input type="radio" value="None" id="radioTwo" name="account"/>
            <label for="radioTwo" class="radio">Company</label>
        </div>
        <hr>
        <label class="icon" for="email"><i class="icon-envelope "></i></label>
        <input type="text" name="email" id="email" placeholder="Email" required/>
        <label class="icon" for="name"><i class="icon-user"></i></label>
        <input type="text" name="name" id="name" placeholder="Name" required/>
        <label class="icon" for="password"><i class="icon-shield"></i></label>
        <input type="password" name="password" id="password" placeholder="Password" required/>
        <div class="gender">
            <input type="radio" value="None" id="male" name="gender" checked/>
            <label for="male" class="radio">Male</label>
            <input type="radio" value="None" id="female" name="gender"/>
            <label for="female" class="radio">Female</label>
        </div>
        <p>By clicking Register, you agree on our <a href="#">terms and condition</a>.</p>
        <input type="submit" class="button" value="Register"/>
    </form>
</div>

</body>
</html>


