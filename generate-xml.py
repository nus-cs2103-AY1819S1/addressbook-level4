#!python3

import lxml.etree as ET
import numpy.random as random
import math
import string
import urllib3

word_site = "http://svnweb.freebsd.org/csrg/share/dict/words?view=co&content-type=text/plain"
name_site = "https://svnweb.freebsd.org/csrg/share/dict/propernames?revision=61767&view=co&pathrev=61767"

http = urllib3.PoolManager()
response = http.request('GET', word_site)
txt = response.data
WORDS = txt.splitlines()
WORDS = list(map(lambda word: word.decode("utf-8"), WORDS))

response = http.request('GET', name_site)
txt = response.data
NAMES = txt.splitlines()
NAMES = list(map(lambda name: name.decode("utf-8"), NAMES))

result = ET.Element("addressbook")

def transform_into_student_id(num):
  length = math.floor(math.log10(num)) + 1
  stringified = str(num)
  while len(stringified) < 7:
    stringified = "0" + stringified
  return "A" + stringified + random.choice(list(string.ascii_uppercase))

def transform_into_phone_number(num):
  length = math.floor(math.log10(num)) + 1
  stringified = str(num)
  while len(stringified) < 8:
    stringified = stringified + "0"
  return stringified

def populateStudents(root):
  student_ids = []
  for i in range(100):
    student_id = transform_into_student_id(random.randint(0, 10000000))
    phone_num = transform_into_phone_number(random.randint(100000, 100000000))

    student = ET.SubElement(root, "students")
    name = ET.SubElement(student, "name")
    name.text = random.choice(NAMES)
    phone = ET.SubElement(student, "phone")
    phone.text = phone_num
    email = ET.SubElement(student, "email")
    email.text = random.choice(WORDS)
    email.text += "_"
    email.text += random.choice(WORDS)
    email.text += "@gmail.com"
    studentId = ET.SubElement(student, "studentId")
    studentId.text = student_id
    student_ids.append(student_id)
    feedback = ET.SubElement(student, "feedback")
    feedback.text = " ".join(random.choice(WORDS, 10, replace=False))
  return student_ids

def populateTutorialGroups(root, student_ids):
  tg_ids = {}
  for i in range(10):
    tg = ET.SubElement(root, "tutorialGroups")
    name = ET.SubElement(tg, "name")

    name_text = "-".join(random.choice(NAMES, 2, replace=False))
    name.text = name_text
    id_text = random.choice(NAMES)
    while tg_ids.get(id_text) is not None:
      id_text = random.choice(NAMES)
    id = ET.SubElement(tg, "id")
    id.text = id_text

    my_students = random.choice(student_ids, random.randint(2, 11), replace=False)
    for student_id in my_students:
      student = ET.SubElement(tg, "studentIds")
      student.text = student_id

    for i in range(random.randint(2, 5)):
      assignment = ET.SubElement(tg, "assignments")
      title = ET.SubElement(assignment, "title")
      assignment_title = ET.SubElement(title, "assignmentTitle")
      ttext = random.choice(WORDS)
      ttext += str(random.randint(1, 10))
      assignment_title.text = ttext
      max_marks = ET.SubElement(assignment, "maxMarks")
      mmarks = random.randint(20, 100)
      max_marks.text = str(mmarks)
      gradebook = ET.SubElement(assignment, "gradebook")
      graded_students = random.choice(my_students, random.randint(1, len(my_students)), replace=False)
      for stid in graded_students:
        grades = ET.SubElement(gradebook, "grades")
        student_id = ET.SubElement(grades, "studentId")
        student_id.text = stid
        marks = ET.SubElement(grades, "marks")
        marks.text = str(random.randint(0, mmarks))

student_ids = populateStudents(result)
populateTutorialGroups(result, student_ids)

ET.ElementTree(result).write("./data/super.xml", pretty_print=True)
