import math
import tkinter as tk

OFFSET = 5000

def main():
    print(f"Hello")

    x = (0,8)
    z = (0,8)
    first_city = (-1904,-1173)
    second_city = (-2484,-2220)
    third_city = (-2188,-3798)

    window = tk.Tk()

    for x_iteration in range(-15,10):
        x_start = ((x_iteration * 20) + x[0]) * 16
        x_end = ((x_iteration * 20) + x[1]) * 16
        for z_iteration in range(-15,10):
            z_start = ((z_iteration * 20) + z[0]) * 16
            z_end = ((z_iteration * 20) + z[1]) * 16
            first = check_city(first_city, x_start, x_end, z_start, z_end)
            second = check_city(second_city, x_start, x_end, z_start, z_end)
            third = check_city(third_city, x_start, x_end, z_start, z_end)

            height = int((z_end - z_start)/16)
            width = int((x_end - x_start)/16)
            # print(f"height: {height}, width: {width}")

            label = tk.Label(master=window, height=height, width=width, bg="green" if first or second or third else "yellow")
            label.place(x=(x_start+OFFSET)/16, y=(z_start+OFFSET)/16)

            print(f"Possible End City in: ({x_start},{z_start}), ({x_start},{z_end}), ({x_end},{z_start}), ({x_end},{z_end})")

    # window.mainloop()


def check_city(city, x_start, x_end, z_start, z_end):
    if x_start < city[0] < x_end and z_start < city[1] < z_end:
        print(f"City at {city} is within grid ({x_start},{z_start}), ({x_start},{z_end}), ({x_end},{z_start}), ({x_end},{z_end})")
        return True


if __name__=="__main__":
    main()


# Known End Cities
# 1. x: -1904, z: -1173
# 2. x: -2484, z: -2220
# 3. x: -2188, z: -3798