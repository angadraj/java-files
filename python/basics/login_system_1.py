def simple_login_system():
    print('########### Create your account ###########')
    username = input('enter username: ')
    passwd = input('enter your paswd: ')

    print('your account has been created successfully!')

    no_of_tries = 0
    while no_of_tries < 3:
        username2 = input('enter username to login: ')
        passwd2 = input('enter password to login: ')
        if (username == username2 and passwd == passwd2):
            print('logged in successfully...')
            break
        else:
            print('invalid credentials! Try Again')
            no_of_tries += 1
            if no_of_tries == 3:
                print('account blocked!')
                break
            
            
# simple_login_system()