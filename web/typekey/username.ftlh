[#ftl output_format="HTML"]
<!DOCTYPE html>
<html lang="en">
<head></head>
<body>[#import "commons.ftlh" as com]
   &lt;@com.main&gt;
      <div class="border border-1 rounded p-5">
         <form id="login_form" method="POST">
            <div class="mb-3 row">
               <div class="col-md-8">
                  <label for="username" class="col-md-4 col-form-label">Enter your username:</label>
                  <input type="text" class="form-control" id="username" name="username">
               </div>
            </div>
            <div class="row">
               <div class="col-md-12 d-flex justify-content-end">
                  <input type="submit" class="btn btn-success" id="login" value="Login">
               </div>
            </div>
         </form>
      </div>
   [/@com.main]</body>


</html>