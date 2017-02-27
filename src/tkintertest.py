#HelloWorld.py
import sys
import tkinter
root = tkinter.Tk()
l =tkinter.Label(root, text="Hello World")
b = tkinter.Button (root, text='Quit', command= root.destroy)
l.pack()
b.pack()
root.mainloop()

