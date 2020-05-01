#
# Example file for working with classes
#


class myClass:
    def method1(self):
        print("myClass method1")

    def method2(self, somestring):
        print(f"myClass method2 {somestring}")


class anotherClass(myClass):
    def method1(self):
        myClass.method1(self)
        print("AnotherClass method1")

    def method2(self, somestring="hey"):
        print(f"AnotherClass method2 {somestring}")


def main():
    c = myClass()
    c.method1()
    c.method2("This is a string")

    c2 = anotherClass()
    c2.method1()
    c2.method2()


if __name__ == "__main__":
    main()
