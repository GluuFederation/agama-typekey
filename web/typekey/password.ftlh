<#import "commons.ftlh" as com>
   <@com.main>
      <div class="border border-1 rounded p-5">
         <form id="login_form" method="POST">
            <div class="mb-3 row">
               <div class="col-md-8">
                  <label for="pwd" class="col-md-4 col-form-label">Enter your password:</label>
                  <input type="password" class="form-control" id="pwd" name="pwd">
               </div>
            </div>
         </form>
      </div>
      <script src="keystroke/logger_pwd.js"></script>
   </@com.main>
