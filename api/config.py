from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    database_url: str
    token_secret: str

    class Config:
        env_file = ".env"


settings = Settings()
