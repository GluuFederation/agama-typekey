<#import "commons.ftlh" as com>
<@com.main>
<form id="login_form" method="POST">
   <input type="password" id="pwd" name="pwd" placeholder="Password">
   <input type="hidden" id="k_password" name="k_password">
   <input type="submit" id="login" value="Login">
</form>
<script src="keystroke/logger_pwd.js"></script>
</@com.main>
