#from django.shortcuts import render

# Create your views here.
from django.http import HttpResponse
def index(request):
    response = HttpResponse()
    response.write("<h1> Welcome </h1>")
    response.write("This is poll app")
    return response
from django.shortcuts import render
# Create your views here.
from django.http import HttpResponse
from login.forms import LoginForm
from . import templates
def loginView(request):
	username = "Wrong username or password"
	if request.method == "POST":
	   MyLoginForm = LoginForm(request.POST)
	   if MyLoginForm.is_valid():
		   if MyLoginForm.cleaned_data['username'] == 'admin':
			   if MyLoginForm.cleaned_data['password'] == '123':
				   username = MyLoginForm.cleaned_data['username']
				   request.session['username'] = username
				   request.session.set_expiry(15);
	else:
	   MyLoginForm = LoginForm()
	return render(request, 'loggedin.html', {'username':username})
def formView(request):
	if request.session.has_key('username'):
		username = request.session['username']
		return render(request, 'loggedin.html', {'username':username})
	else:
		return render(reuqest, 'login.html', {})
def logoutView(request):
	try:
		del request.session['username']
	except:
		pass
	return HttpResponse("Good bye!")
