package by.x1ss.ModsenPractice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IllegalOperation extends ServiceBaseException {
    private char operation = 0;

    @Override
    public String getMessage() {
        if (operation == '(' || operation == ')') {
            return "Brackets are incorrect";
        } else if (operation != 0) {
            return "Operation is incorrect : " + operation;
        } else {
            return "Can't find operation";
        }
    }
}
