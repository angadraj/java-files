# from math import *
print('hello')
# string template

# variables and data types (it will take value you assign to variable name)
angad_raj = 'giraffe Academy';
# print(angad_raj[0]);

# numbers
my_num = 20.20;
# print(ceil(my_num));

# taking input
# my_input = input('enter name');
# print(my_input)

# lists 
arr = [1, 2, 3, 4, 5, 6];
#  get in range
arr[1:];
arr[1: 3]; # 3 idx is excluded
arr[-1]
# print(arr[-1]);  this gives last element from an arr
# append list
# arr1.extend(arr2);
# arr1.append(number);
# arr1.insert(idx, value);
# arr.remove(value);
# arr.clear(); -> empty the array
# arr.pop(); -> removes the last element 
# arr.index(value); -> gives index of an value if present else -1
# arr.count(value); -> counts the duplicated values.
# arr.sort(); -> numbers in asc order and words in lexical order
# arr.copy(); -> makes a copy of an arr

# tuples
cordinates = (4, 5)
# they are immutable, values can't be changed
# they are used to declare data which we don't want to change
cordinates = (6, 7); 
# they can be assign again

# functions 

def say_hello(name, age):
    return [name, age]
# array = say_hello('Angad', 100)
# print(array)

# control flow
# | => or, & -> and, ! as not
a, b = 10, 20
if a < b and a == b:
    print('a is small')
else : 
    print('a is big')

# compare numbers and strings
def greater(num1, num2, num3):
    ele = max(num1, max(num2, num3));
    return ele
my_greater = greater(1, 2, -2);
print(my_greater);

print('yes' if 'angad' == 'angad' else 'no')
# do if condition else do_something_else

##################

