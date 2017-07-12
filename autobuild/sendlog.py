#!/usr/bin/python

import smtplib
from email.mime.text import MIMEText

FROM = "git@toshiba-tsdv.com"
TO = ["romlinux@toshiba-tsdv.com"]

def mail_test():
    msg = MIMEText(open("/home/luyentm/A5JT-luyentm/log-send-mail", "rb").read())
    msg['Subject']= "[Automatic Building Result]"
    msg['From']   = FROM
    msg['To']     = ", ".join(TO)
    server = smtplib.SMTP("10.116.17.246")
    server.sendmail(FROM, TO, msg.as_string())
    server.quit()

if __name__ == "__main__":
    mail_test()

