package com.teamchallenge.markethub.controller.filter;

import java.util.List;

public record ItemRequestParams(double priceFrom, double priceTo, String available, List<String> brand) {
}
