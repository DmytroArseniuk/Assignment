package com.srost_studio.assignment.services.commands;

import android.content.Intent;

import com.srost_studio.assignment.MainApplication;

public interface Command {
    void execute(MainApplication application, Intent intent);
}
