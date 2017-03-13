from django.shortcuts import render
#add reference
from django.http import HttpResponse, Http404
from django.template import loader

from .models import Question
def detail(request, question_id):
    try:
        question = Question.objects.get(pk=question_id)
    except Question.DoesNotExist:
        raise Http404("Question does not exist")
    return render(request, 'polls/detail.html', {'question':question})
# def detail(request, question_id):
#     return HttpResponse("You're looking at question %s." % question_id)

# def results(request, question_id):
#     response = "You're looking at the results of question %s."
#     return HttpResponse(response % question_id)
from django.shortcuts import get_object_or_404, render
def results(request, question_id):
    question = get_object_or_404(Question, pk=question_id)
    return render(request, 'polls/results.html', {'question': question})
# def vote(request, question_id):
#     return HttpResponse("You're voting on question %s." % question_id)
from .crawl import termBrief
def vote(request, question_id):
    selected_choice = request.POST['FirstName']
    para = termBrief(selected_choice)
    template = loader.get_template('polls/results.html')
    context = {
        'content': para,
    }
    return HttpResponse(template.render(context, request))
    # return HttpResponse(para)
    # question = get_object_or_404(Question, pk=question_id)
    # try:
    #     # selected_choice = question.choice_set_get(pk=request.POST['choice'])
    #
    # except (KeyError, Choice.DoesNotExist):
    #     return render(request, 'polls/detail.html', {
    #         'question': question,
    #         'error_message': "You didn't select a choice.",
    #     })
    # else:
    #     selected_choice.votes += 1
    #     selected_choice.save()
    # return HttpResponseRedirect(reverse('polls:results', args=(question.id,)))
# def index(request):
#     latest_question_list = Question.objects.order_by('-pub_date')[:5]
#     output = ', '.join([q.question_text for q in latest_question_list])
#     return HttpResponse(output)
def index(request):
    try:
        from django.utils import timezone
        q = Question(question_text="What's search?", pub_date=timezone.now())
        q.save()
        latest_question_list = Question.objects.order_by('-pub_date')[:1]
        template = loader.get_template('polls/index.html')
        context = {
            'latest_question_list': latest_question_list,
        }
    except Question.DoesNotExist:
        raise Http404("Question does not exist")
    return HttpResponse(template.render(context, request))
    #
    # latest_question_list = Question.objects.order_by('-pub_date')[:5]
    # template = loader.get_template('polls/index.html')
    # context = {
    #     'latest_question_list': latest_question_list,
    # }
    # return HttpResponse(template.render(context, request))
# Create your views here.
