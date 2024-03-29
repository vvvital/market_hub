package com.teamchallenge.markethub.controller.filter;

import java.util.List;

public record ItemsFilterParams( long id, double priceFrom, double priceTo, String available, List<String> brands) {
}
