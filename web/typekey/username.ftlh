[#ftl output_format="HTML"]
<!DOCTYPE html>
<html lang="en">
<head></head>
<body>[#import "commons.ftlh" as com]
   [@com.main]
      <div class="border border-1 rounded p-5"><form method="POST" id="login_form"><div class="mb-3 row"><div class="col-md-8"><label for="username" class="col-md-4 col-form-label">Enter your username:</label><input type="text" id="username" name="username" class="form-control"></div></div><div class="row"><div class="col-md-12 d-flex justify-content-end"><input type="submit" id="login" value="Login" class="btn btn-success"></div></div></form></div>
   [/@com.main]</body>


</html>