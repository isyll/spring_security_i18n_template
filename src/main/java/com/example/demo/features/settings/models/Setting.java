package com.example.demo.features.settings.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(unique = true, nullable = false)
    private String key;

    @JsonIgnore
    @Column(columnDefinition = "TEXT")
    private String value;

    @JsonIgnore
    @JsonProperty(value = "value_type")
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private SettingType valueType;

    @Transient
    private String description;

    @Transient
    private String name;

    @JsonProperty("value")
    public Object getTypedValue() {
        switch (valueType) {
            case SettingType.Integer:
                return Integer.parseInt(value);
            case SettingType.Double:
                return Double.parseDouble(value);
            case SettingType.Boolean:
                return Boolean.parseBoolean(value);
            case SettingType.String:
                return value;
            default:
                throw new IllegalArgumentException("Unsupported value type: " + valueType);
        }
    }

    public boolean checkType() {
        try {
            switch (valueType) {
                case Integer:
                    Integer.parseInt(value);
                    break;
                case Double:
                    Double.parseDouble(value);
                    break;
                case Boolean:
                    Boolean.parseBoolean(value);
                    break;
                case String:
                    // No need to check, any value is valid for String
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported value type: " + valueType);
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void setValue(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }

        if (obj instanceof Integer) {
            this.value = obj.toString();
            this.valueType = SettingType.Integer;
        } else if (obj instanceof Double) {
            this.value = obj.toString();
            this.valueType = SettingType.Double;
        } else if (obj instanceof Boolean) {
            this.value = obj.toString();
            this.valueType = SettingType.Boolean;
        } else if (obj instanceof String) {
            this.value = (String) obj;
            this.valueType = SettingType.String;
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + obj.getClass().getSimpleName());
        }
    }
}
