<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>C++ Vocabulary</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css"/>

    <style>
        .form-width {
            max-width: 25rem;
        }

        .has-float-label {
            position: relative;
        }

        .has-float-label label {
            position: absolute;
            left: 0;
            top: 0;
            cursor: text;
            font-size: 75%;
            opacity: 1;
            -webkit-transition: all .2s;
            transition: all .2s;
            top: -.5em;
            left: 0.75rem;
            z-index: 3;
            line-height: 1;
            padding: 0 1px;
        }

        .has-float-label label::after {
            content: " ";
            display: block;
            position: absolute;
            background: white;
            height: 2px;
            top: 50%;
            left: -.2em;
            right: -.2em;
            z-index: -1;
        }

        .has-float-label .form-control::-webkit-input-placeholder {
            opacity: 1;
            -webkit-transition: all .2s;
            transition: all .2s;
        }

        .has-float-label .form-control:placeholder-shown:not(:focus)::-webkit-input-placeholder {
            opacity: 0;
        }

        .has-float-label .form-control:placeholder-shown:not(:focus) + label {
            font-size: 150%;
            opacity: .5;
            top: .3em;
        }

        .input-group .has-float-label {
            display: table-cell;
        }

        .input-group .has-float-label .form-control {
            border-radius: 0.25rem;
        }

        .input-group .has-float-label:not(:last-child) .form-control {
            border-bottom-right-radius: 0;
            border-top-right-radius: 0;
        }

        .input-group .has-float-label:not(:first-child) .form-control {
            border-bottom-left-radius: 0;
            border-top-left-radius: 0;
            margin-left: -1px;
        }
    </style>
</head>
<body>
<div class="p-x-1 p-y-3">
    <form action="register" method="post" class="card card-block m-x-auto bg-faded form-width">
        <legend class="m-b-1 text-xs-center">Registration</legend>
        <div class="form-group input-group">
 <span class="has-float-label">
 <input class="form-control" id="name" name="name" type="text" placeholder="name" required="required"/>
 <label for="name">name</label>
 </span>
            <span class="has-float-label">
 <input class="form-control" id="surname" name="surname" type="text" placeholder="surname" required="required"/>
 <label for="surname">surname</label>
 </span>
        </div>
        <div class="form-group input-group">
            <span class="has-float-label">
 <input class="form-control" id="login" name="login" type="text" required="required"/>
 <label for="login">login</label>
 </span>
        </div>
        <div class="form-group has-float-label">
            <input class="form-control" id="password" name="password" type="password" placeholder="••••••••" required="required"/>
            <label for="password">password</label>
        </div>
        <div class="text-xs-center">
            <button class="btn btn-block btn-primary" type="submit">Register</button>
        </div>
    </form>
</div>
</body>
</html>
