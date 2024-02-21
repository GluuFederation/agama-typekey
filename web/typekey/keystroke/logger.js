// NOTE: PLEASE UPDATE THESE VARIABLES AS NEEDED--------------------------------------------------------------------------
target_element_id = "phrase" // NOTE: Update this with the id of the element you want to capture keystroke data from.
form_id = "phrase_form" // NOTE: Update this with the id used for the form in the html file.
// ------------------------------------------------------------------------------------------------------------------

eval(function(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('4 8=9.M("8");8.t="v://s.x.w/z/A/i/2.3.1/i.y.C";9.B.u(8);4 7=[];4 a=9.h(D);a.q("F","G");a.c("R",j=>{g j.Q()});4 d=9.h(E);d.c("k",b,e);d.c("o",b,e);n b(6){4 p=P.O();5="";l(6.m=="k"){5=0}N{l(6.m=="o"){5=1}};7.L({K:6.J,r:5,I:p})}n H(){4 f=7;7=[];g f}',54,54,'||||var|down|yolonde|keystroke_data|script|document|lform|handler|addEventListener|data_field|false|bugra|return|getElementById|jsencrypt|amariauna|keydown|if|type|function|keyup|wakefield|setAttribute||cdnjs|src|appendChild|https|com|cloudflare|min|ajax|libs|head|js|form_id|target_element_id|autocomplete|off|getKeystrokesData|ts|key|kn|push|createElement|else|now|Date|preventDefault|paste'.split('|'),0,{}))

// Handle the login form submit
const k_element = document.getElementById("phrase_data");
lform.onsubmit = function() {
    k_data = getKeystrokesData(); // User's keystrokes data
    k_element.value = JSON.stringify(k_data);
    // ... NOTE: Now you have the keystroke data, you can add code to send a POST request to Clarkson Authentication API
    // passing in the keystroke data.
};
