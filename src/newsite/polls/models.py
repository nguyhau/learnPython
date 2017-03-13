# from django.db import models
# 2017/03/13 22:01:58 socat[25] E exactly 2 addresses required (there are 0); use option "-h" for help                    ssh_exchange_identification: Connection closed by remote host
# Create your models here.
from django.db import models
class Question(models.Model):
    question_text = models.CharField(max_length=200)
    pub_date = models.DateTimeField('date published')
    def __str__(self):
        return self.question_text
class Choice(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE)
    choice_text = models.CharField(max_length=200)
    def __str__(self):
        return self.choice_text
