<#import "commons.ftlh" as com>
   <@com.main>
      <div class="border border-1 rounded p-5">
         <form id="phrase_form" method="POST">
            <div class="mb-3 row">
               <div class="col-md-8">
                  <label for="phrase" class="col-md-4 col-form-label">Type the following phrase:<br>
                    ${phrase!}
                  </label>
                  <input type="text" class="form-control" id="phrase" name="phrase">
               </div>
            </div>
            <input type="hidden" id="phrase_data" name="phrase_data">
            <div class="row">
               <div class="col-md-12 d-flex justify-content-end">
                  <input type="submit" class="btn btn-success" id="login" value="Login">
               </div>
            </div>
         </form>
      </div>
      <script src="keystroke/logger.js"></script>
   </@com.main>
