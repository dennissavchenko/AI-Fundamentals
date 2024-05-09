from cluster import Cluster


class UI:
    def __init__(self):
        self.cluster = []
        self.k = 0

    def change_k(self):
        k = int(input("Enter the k of the cluster: "))
        self.cluster.group(k)

    def change_file(self):
        file_found = False
        while not file_found:
            file_name = input("Enter name of the file with the data to group: ")
            try:
                self.cluster = Cluster(file_name)
                file_found = True
            except IOError:
                print("File was not found. Try again!")
        self.change_k()

    def start(self):
        print("Welcome to cluster!")
        self.change_file()
        self.menu()

    def menu(self):
        print("Menu: ")
        print("1 -> Assign case to a group")
        print("2 -> Show pair plot")
        print("3 -> Change the k")
        print("4 -> Print members of each cluster")
        print("Any other key -> Change the file")
        choice = input("Enter your choice: ")
        if choice == "1":
            point = [float(value) for value in input("Enter data of the case: ").split(',')]
            print(point)
            print(f'Point was assigned to {self.cluster.classify(point)} class')
            self.menu()
        elif choice == '2':
            self.cluster.create_pair_plot()
            self.menu()
        elif choice == '3':
            self.change_k()
            self.menu()
        elif choice == '4':
            self.cluster.print_members()
            self.menu()
        else:
            self.change_file()
            self.menu()
