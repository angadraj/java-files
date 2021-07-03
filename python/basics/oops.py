class Student:
    # all the needy things will be in __init__
    # you can use all those things using "self" keyword
    teacher = 'Rekha'
    principal = 'Nangia'
    # this will be same in all instances
    def __init__(self, name, subject):
        self.name = name
        self.subject = subject
        self.data = []

    def marks(self, score):
        self.data.append(score)

s1 = Student('Angad', 'maths')
s2 = Student('Aman', 'eng')
print(s1.teacher)
print(s2.teacher)

# basically self refers to the current instance that calls the class methods
