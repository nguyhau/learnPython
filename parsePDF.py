
# from io import StringIO
# from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
# from pdfminer.converter import TextConverter
# from pdfminer.layout import LAParams
# from pdfminer.pdfpage import PDFPage
#
# def convert(fname, pages=None):
#     if not pages:
#         pagenums = set()
#     else:
#         pagenums = set(pages)
#
#     output = StringIO()
#     manager = PDFResourceManager()
#     converter = TextConverter(manager, output, laparams=LAParams())
#     interpreter = PDFPageInterpreter(manager, converter)
#
#     infile = file(fname, 'rb')
#     for page in PDFPage.get_pages(infile, pagenums):
#         interpreter.process_page(page)
#     infile.close()
#     converter.close()
#     text = output.getvalue()
#     output.close
#     return text
# para = convert('Linux System Programming.pdf', pages=[5,7])
# print (para)

from pdfminer.pdfparser import PDFParser, PDFDocument
from pdfminer.pdfinterp import PDFResourceManager, PDFPageInterpreter
from pdfminer.converter import PDFPageAggregator
from pdfminer.layout import LAParams, LTTextBox, LTTextLine

fp = open('Linux System Programming.pdf', 'rb')
parser = PDFParser(fp)
doc = PDFDocument()
parser.set_document(doc)
doc.set_parser(parser)
doc.initialize('')
rsrcmgr = PDFResourceManager()
laparams = LAParams()
device = PDFPageAggregator(rsrcmgr, laparams=laparams)
interpreter = PDFPageInterpreter(rsrcmgr, device)
# Process each page contained in the document.
for page in doc.get_pages():
    interpreter.process_page(page)
    layout = device.get_result()
    for lt_obj in layout:
        if isinstance(lt_obj, LTTextBox) or isinstance(lt_obj, LTTextLine):
            print(lt_obj.get_text())
