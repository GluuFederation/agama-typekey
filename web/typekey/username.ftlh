<#import "commons.ftlh" as com>
<@com.main>
<form id="login_form" method="POST">
   <input type="text" id="username" name="username" placeholder="Email">
   <input type="hidden" id="k_username" name="k_username">
   <input type="submit" id="login" value="Login">
</form>
<script src="keystroke/logger_username.js"></script>
</@com.main>
