package com.github.jon7even.presentation.view;

import lombok.Data;

import java.util.Scanner;

@Data
public abstract class FacadeFrame {
    private Scanner scanner = new Scanner(System.in);

    public abstract void handle();
}
