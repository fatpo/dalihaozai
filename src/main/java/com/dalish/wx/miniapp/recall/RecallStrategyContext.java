package com.dalish.wx.miniapp.recall;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public enum RecallStrategyContext {

    ITEM_CF("itemCfRecallService") {
        @Override
        public List<String> recall(Object recallModel) {
            return getRecallStrategy().recall(recallModel);
        }
    },
    USER_CF("userCfRecallService") {
        @Override
        public List<String> recall(Object recallModel) {
            return getRecallStrategy().recall(recallModel);
        }
    };

    public abstract List<String> recall(Object recallModel);

    String value;


    RecallStrategyContext(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Getter
    @Setter
    public RecallStrategy recallStrategy;


    @Component
    public static class HumanEnumInjector{

        @Autowired
        Map<String, RecallStrategy> recallMap;

        @PostConstruct
        public void init(){
            for (RecallStrategyContext humanEnum : EnumSet.allOf(RecallStrategyContext.class)){
                humanEnum.setRecallStrategy(recallMap.get(humanEnum.value));
            }
        }

    }
}
