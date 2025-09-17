import random

def main():
    # Запрашиваем число N у пользователя
    while True:
        try:
            N = int(input("Введите число N (N >= 3): "))
            if N >= 3:
                break
            else:
                print("Число должно быть не меньше 3!")
        except ValueError:
            print("Пожалуйста, введите целое число!")
    
    # Создаём двумерный массив NxN со случайными числами
    array = []
    for i in range(N):
        row = []
        for j in range(N):
            row.append(random.randint(1000, 9999))
        array.append(row)
    
    print("\nСгенерированный массив:")
    for row in array:
        print(row)
    
    # Находим минимальный элемент побочной диагонали
    min_element = find_min_secondary_diagonal(array)
    
    print(f"\nМинимальный элемент побочной диагонали: {min_element}")

def find_min_secondary_diagonal(array):
    """
    Находит минимальный элемент побочной диагонали матрицы
    """
    n = len(array)
    min_value = 9999
    
    # Проходим по элементам побочной диагонали
    for i in range(n):
        j = n - 1 - i  # Индекс для побочной диагонали
        element = array[i][j]
        if element < min_value:
            min_value = element
    
    return min_value

if __name__ == "__main__":
    main()