from math import *
'''
code with tomi

from math import *

 print('hello')
 string template
 print(f'{var_name} your sentence')
 variables and data types (it will take value you assign to variable name)
angad_raj = 'giraffe Academy'
 print(angad_raj[0]);

 numbers
my_num = 20.20
 print(ceil(my_num));

 taking input
 my_input = input('enter name');
 print(my_input)

 lists
arr = [1, 2, 3, 4, 5, 6]
  get in range
arr[1:]
arr[1: 3]   3 idx is excluded
arr[-1]
 arr = ['Angad', True, 100, 5.4, 'string']; type will be list1
 print(arr[-1]);  this gives last element from an arr
 append list
 arr1.extend(arr2);
 arr1.append(number);
 arr1.insert(idx, value);
 arr.remove(value);
 arr.clear(); -> empty the array
 arr.pop(); -> removes the last element
 arr.index(value); -> gives index of an value if present else -1
 arr.count(value); -> counts the duplicated values.
 arr.sort(); -> numbers in asc order and words in lexical order
 arr.copy(); -> makes a copy of an arr
 arr.reverse() -> reverse the array

 tuples
cordinates = (4, 5)
 they are immutable, values can't be changed
 they are used to declare data which we don't want to change
 you can store different data types in it same as lists
 value access in tupples = tuples[idx]
cordinates = (6, 7)
 they can be assign again

 functions


def say_hello(name, age):
    return [name, age]


def fn(*names):
    print(f'hello {names[1]}')


fn('angad', 2, '100')
 array = say_hello('Angad', 100)
 print(array)


 control flow
 | => or, & -> and, ! as not
 a, b = 10, 20
 if a < b and a == b:
      print('a is small')
 else:
 print('a is big')

 compare numbers and strings


def greater(num1, num2, num3):
    ele = max(num1, max(num2, num3))
    return ele


my_greater = greater(1, 2, -2)
 print(my_greater)

print('yes' if 'angad' == 'angad' else 'no')
 ternary operator
 do if condition else do_something_else



name = 22.3
 print(f'{name / 3} is hello')

 print(f'hello number {number} and i am {2} yo wahtup')



 dictionaries
 no duplicacy allowed in this
 values can be same but not keys
my_dict = {
    "name": "Angad",
    "class_name": "IT-1",
    "roll_no": "03",
    "qualification": "engineering",
    "friends": ['aman', 'arjun', 'bikram']
}
 print(my_dict)



 loops while

i = 0
while i < 5:
     print(i)
    i += 1

 for loops
 my_list = ['angad' 'gul', 'pari']
 for keys in range(10, 15):
      print(keys)



 2d lists
r, c = 2, 2
my_grid = [[-1, 0, 10], [0, 1, 11], [1, 0, 12], [0, -1, 13]]

for dir in my_grid:
    for d in dir:
        print(d, end=" ")
    print()

# try and except
try:
    print(floor(5 / 5))
except ZeroDivisionError: 
    print('ZeroDivisionError')
else: 
    print('all good')
finally: 
    print('executed finally block')

# get dir name
import os
cwd = os.getcwd()
print(cwd)

'''

# files management
one_txt = open('/Users/akashverma/Desktop/java-files/python/one.txt', 'a')
# print(one_txt.read())
for i in range (10):
    one_txt.write(f'\nhello I am new text {i} in one.txt')
# print(one_txt.read())
one_txt.close()
