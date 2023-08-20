#include <iostream>

// Function to calculate factorial
int factorial(int n) {
    if (n <= 1) {
        return 1;
    } else {
        return n * factorial(n - 1);
    }
}

int main() {
    int num;

    // Input
    std::cout << "Enter a positive integer: ";
    std::cin >> num;

    if (num < 0) {
        std::cout << "Please enter a positive integer.\n";
    } else {
        // Calculate factorial and output the result
        int result = factorial(num);
        std::cout << "Factorial of " << num << " is " << result << "\n";
    }

    return 0;
}
