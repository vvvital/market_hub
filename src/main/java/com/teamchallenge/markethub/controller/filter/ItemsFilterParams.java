package com.teamchallenge.markethub.controller.filter;

import java.util.List;

public record ItemsFilterParams(double priceFrom, double priceTo, String available, List<String> brands) {
}
