if (isPhase(PRE_INIT)) {
    setName('Test');
    setVersion('1.0.0');
}

if (!isPhase(INIT)) {
    return;
}

encode(1, 'No such function');
encode(3, 'Invalid number');

register('exit') { a ->
    if (a.length == 0) {
        return 2;
    }

    try {
        return Integer.decode(a[0]);
    } catch (NumberFormatException e) {
        return 3;
    }
}

register('dump-all') { a ->
    println(this);
    return 0;
}
