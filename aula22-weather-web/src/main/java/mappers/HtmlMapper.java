package mappers;

import html.Element;

public interface HtmlMapper<T> {
    Element map(T entity);
}
